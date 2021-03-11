(ns coffee-chat-roulette.utils.map-test
  (:require [clojure.test :refer :all])
  (:require [coffee-chat-roulette.utils.map :refer [update-int]]))

(def ^:private test-data {:a {:b 1} :c 2})

(defn- update-int-check
  [path f expected]
  (let [result (update-int test-data path f)]
    (= expected (get-in result path))))

(deftest update-int-test
  (testing "Update map"
    (testing "when value is not set yet"
      (is (update-int-check [:d] identity 0)
          "Should return 0 when calling identity on unset value")
      (is (update-int-check [:a :c] inc 1)
          "Should return 1 when incrementing unset value")
      (is (update-int-check [:d :c] dec -1)
          "Should return 0 when decrementing unset value"))
    (testing "when value is already set"
      (is (update-int-check [:a :b] identity 1)
          "Should return 1 when calling identity on value=1")
      (is (update-int-check [:a :b] inc 2)
          "Should return 2 when incrementing value=1")
      (is (update-int-check [:c] dec 1)
          "Should return 0 when decrementing value=1"))))
