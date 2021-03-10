(ns coffee-chat-roulette.core
  (:require [coffee-chat-roulette.storage.names :as names])
  (:gen-class))

(-> "Pierre"
    (names/create)
    :id
    (names/get-by-id))
(names/create "Jean")
(-> "Luc"
    (names/create)
    :id
    (names/delete))
(names/create "Sam")

(defn -main
  [& args]
  (println "Names: " (names/get-all))
  (println "Size: " (names/size)))
