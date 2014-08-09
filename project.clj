(defproject whodidthis/clj-pouch "0.1.1-SNAPSHOT"
  :description "Wrapper for PouchDB in ClojureScript"
  :url "https://github.com/whodidthis/clj-pouch"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async  "0.1.278.0-76b25b-alpha"]
                 [com.pouchdb/pouchdb "2.2.3"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]]
  :aliases  {"run-tests" ["do" "clean," "cljsbuild" "once" "test"]
             "auto-test" ["do" "clean," "cljsbuild" "auto" "test"]}
  :cljsbuild {:builds {:test {:source-paths ["src/" "test/"]
                              :notify-command ["phantomjs" :cljs.test/runner "target/js/test.js"]
                              :compiler {:output-to "target/js/test.js"
                                         :optimizations :whitespace
                                         :pretty-print true
                                         :preamble ["es5-shim.js" #_"idb-shim.js" "pouchdb/pouchdb.min.js"]
                                         :externs [#_"pouchdb/externs/pouchdb.js"]}}}})

