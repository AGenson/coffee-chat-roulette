(ns coffee-chat-roulette.utils.file
  (:require [clojure.edn :as edn])
  (:refer-clojure :exclude [load])
  (:import (java.io FileNotFoundException)))

(defn save
  [filename data]
  (spit filename (prn-str data)))

(defn load
  [filename]
  (try
    (edn/read-string (slurp filename))
    (catch FileNotFoundException _ {})))
