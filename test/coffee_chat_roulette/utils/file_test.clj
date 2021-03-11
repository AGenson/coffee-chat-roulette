(ns coffee-chat-roulette.utils.file-test
  (:require [clojure.test :refer :all])
  (:require [clojure.java.io :as io]
            [coffee-chat-roulette.utils.file :as file]))

(def ^:private test-filename1 "test-data1.edn")
(def ^:private test-filename2 "test-data2.edn")
(def ^:private test-data1 {:a 0 :b 1 :c 3})
(def ^:private test-data2 {:a 4 :b 5 :c 6})

(defn exists?
  [filename]
  (.exists (io/file filename)))

(defn- save-checks
  [filename data]
  (file/save filename data)
  (is (exists? filename)
      (str "File not found: " filename))
  (if (exists? filename)
    (is (= (slurp filename) (prn-str data))
        "Data saved in file does not match original data")))

(deftest save-test
  (io/delete-file test-filename1 true)
  (testing "Saving data to file"
    (testing "when file does not exist yet"
      (save-checks test-filename1 test-data1))
    (testing "when file already exists"
      (save-checks test-filename1 test-data2)))
  (io/delete-file test-filename1))

(deftest load-test
  (file/save test-filename1 test-data1)
  (testing "Loading data from file"
    (testing "when file does not exist"
      (is (= {} (file/load test-filename2))
          "Should return empty map when could not load from file"))
    (testing "when file exists"
      (is (= test-data1 (file/load test-filename1))
          "Data loaded from file does not match original data")))
  (io/delete-file test-filename1))
