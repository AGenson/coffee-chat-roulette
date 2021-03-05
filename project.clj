(defproject coffee-chat-roulette "0.1.0-SNAPSHOT"
  :description "Coffee Chat Roulette"
  :url "https://github.com/AGenson/coffee-chat-roulette"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot coffee-chat-roulette.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
