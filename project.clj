(defproject whodidthis/clj-pouchdb "0.1.0-SNAPSHOT"
  :description "Wrapper for PouchDB in ClojureScript"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async  "0.1.278.0-76b25b-alpha"]
                 [com.pouchdb/pouchdb "2.2.3-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]]
  :aliases  {"run-tests" ["do" "clean," "cljsbuild" "once" "test"]
             "auto-test" ["do" "clean," "cljsbuild" "auto" "test"]}
  ;:hooks [leiningen.cljsbuild]
  ;:source-paths ["src"]
  ;:test-paths ["test" "src"]
  ;:profiles {:dev {:dependencies [[com.cemerick/piggieback "0.1.3"]
  ;                                [weasel "0.3.0"]]}
  ;           :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
  :cljsbuild {;:repl-listen-port 9191
              :builds {:test {:source-paths ["src/" "test/"]
                              :notify-command ["phantomjs" #_:cljs.test/runner "runners/myrunner.js" "target/js/test.js"
                                               "--web-security=false" "--disk-cache=true"]
                              :compiler {:output-to "target/js/test.js"
                                         :optimizations :simple
                                         :pretty-print true
                                         :preamble ["pouchdb/es5-shim.js" "pouchdb/pouchdb.js" #_"pouchdb/pouchdb-req.js"]
                                         :externs [#_"pouchdb/externs/pouchdb.js"]}}}})

