(ns coffee-chat-roulette.utils.map)

(defn update-int
  "Updates an integer in a given map, using a given function and path.
  If value under path is nil, will first initialise with 0."
  [map path f]
  (assoc-in map path (f (get-in map path 0))))
