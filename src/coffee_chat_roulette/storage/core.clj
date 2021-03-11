(ns coffee-chat-roulette.storage.core
  (:require [coffee-chat-roulette.utils.file :as file]))

(def ^:private db-filename "db.edn")

(def ^:private db
  "Atom representing the db, first initialised from file.
  A watcher function saves the db state in file every time it is updated."
  (let [db-atom (atom (file/load db-filename))]
    (add-watch db-atom :watcher (fn [_ _ _ new-state] (file/save db-filename new-state)))
    db-atom))

(defn- db-get
  "Returns fetched data from db given a path."
  [path]
  (get-in @db path))

(defn- db-update
  "Updates db data a from given function and path. Returns the updated data."
  [f path & args]
  (apply swap! db f path args)
  (db-get path))

(defn- update-int
  "Updates an integer in a given map, function and path.
  If value under path is 'nil', will first initialise with 0."
  [map path f]
  (assoc-in map path (f (get-in map path 0))))

(defn- next-id
  "Returns the next id for a given entity (auto increment)."
  [entity]
  (db-update update-int [:auto-inc entity] inc))

(defn- size
  "Returns the size of a given entity."
  [entity]
  (db-get [:size entity]))

(defn- find-all
  "Returns the full list of entity elements given an entity."
  [entity]
  (map second (db-get [entity])))

(defn- find-by-id
  "Returns an entity element given an entity and the element's id."
  [entity id]
  (db-get [entity id]))

(defn- save
  "Saves or updates an entity element given an entity and the element."
  [entity data]
  (let [persisted (if-let [data-id (:id data)] (find entity data-id))
        id        (or (:id persisted) (next-id entity))
        formatted (assoc data :id id)]
    (db-update update-int [:size entity] inc)
    (db-update assoc-in [entity id] formatted)))

(defn- delete
  "Deletes an entity element given an entity and the element's id.
  Returns the deleted element."
  [entity id]
  (when-let [element (find-by-id entity id)]
    (db-update update-int [:size entity] dec)
    (db-update update-in [entity] #(dissoc % id))
    element))

(defn- op-name->op
  "Returns the db operation function given its related name."
  [op-name]
  (case op-name
    :save     save
    :delete   delete
    :find-all find-all
    :find     find-by-id
    :size     size))

(defn entity-handler
  "Returns an entity handler to use for db operations on a given entity."
  [entity]
  (fn [op-name & args]
    (let [op (op-name->op op-name)]
      (apply op entity args))))
