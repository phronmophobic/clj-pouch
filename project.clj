(defproject whodidthis/clj-pouch "0.1.1-SNAPSHOT"
  :description "Wrapper for PouchDB in ClojureScript"
  :url "https://github.com/whodidthis/clj-pouch"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.28"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljsjs/pouchdb "3.5.0-0"]]
  :plugins [[lein-cljsbuild "1.1.0"]
            [com.cemerick/clojurescript.test "0.3.1"]]
  :aliases  {"run-tests" ["do" "clean," "cljsbuild" "once" "test"]
             "auto-test" ["do" "clean," "cljsbuild" "auto" "test"]}
  :cljsbuild {:builds {:test {:source-paths ["src/" "test/"]
                              :compiler {:output-to "target/js/test.js"
                                         :optimizations :whitespace
                                         ;; :preamble ["es5-shim.js" #_"idb-shim.js" #_"pouchdb/pouchdb.min.js" ]
                                         :pretty-print true
                                         }}}
              :test-commands {"unit-tests" ["phantomjs"
                                            :runner
                                            "target/js/test.js"]}})

