(ns coffee-chat-roulette.storage.names
  (:require [coffee-chat-roulette.storage.core :refer [entity-handler]]))

(def ^:private names-handler
  "Entity handler for :names"
  (entity-handler :names))

(defn create
  "Creates a new name. Returns the db data created."
  [name]
  (names-handler :save {:name name}))

(defn delete
  "Deletes a name given its id."
  [id]
  (names-handler :delete id))

(defn get-all
  "Returns the full list of names."
  []
  (names-handler :find-all))

(defn get-by-id
  "Returns a name given its id."
  [id]
  (names-handler :find id))

(defn size
  "Returns the number of existing names."
  []
  (names-handler :size))
