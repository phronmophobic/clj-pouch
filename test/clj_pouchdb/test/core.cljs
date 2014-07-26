(ns clj-pouchdb.test.core
  "Some unit tests for the PuchDB wrapper"
  (:require-macros [cemerick.cljs.test :refer [deftest testing is done]] 
                   [cljs.core.async.macros :refer [go alt!]])
  (:require [cemerick.cljs.test :as t] 
            [clj-pouchdb.core :as core]
            [cljs.core.async :as async :refer [close! timeout <! >! timeout chan put!]]))

(deftest my-first-test 
  (testing "i wish i worked"
    (is (= 1 1))))

(deftest ^:async my-second-test
  (testing "totally hoping"
    (let [c (chan 1)]
      (go (let [res (<! c)]
            (is (= res 33))
            (done)))
      (go (<! (timeout 10000)) 
          (put! c 3)
          (close! c)))))

#_(set-print-fn! (fn [& xs] (.log js/console (apply str xs))))
#_(def DB #_"make" "http://127.0.0.1:5984/dubu")
#_(declare db)

#_(core/debug! true)

#_(defn- reset-db []
  (set! db (do (core/destroy-db DB) (core/create-db DB))))

#_(deftest ^:async aaaaa
  (testing "fas"
    (let [db (js/PouchDB. "dubu")]
      (.allDocs db #js {:include_docs true}
        (fn [err, res]
          (is (= err ""))
          (is (= (:total_rows res) 0))
          (done))))))

(deftest ^:async clj-pouch-tests
  (go
    (testing "empty DB has no docs"
      (let [db (core/create-db "dubu")
            res (<! (core/all-docs db {"include-docs" true}))]
        (is (= (:total_rows res) 0))
        (done)))))

(comment (deftest ^:async clj-pouch-tests
  (go

   (testing "empty DB has no docs"
     (reset-db)
     (let [c (core/all-docs db {"include-docs" true})
           res (<! c)]
       (is (= (:total_rows res) 0))))

   (testing "adding doc yields singleton DB"
     (reset-db)
     (<! (core/post-doc db {:name "David" :age 46}))
     (let [res (<! (core/all-docs db))]
       (is (= (:total_rows res) 1))))

   (testing "getting non-existing doc fails"
     (reset-db)
     (is (:error (<! (core/get-doc db "foo")))))

   (testing "updating doc works"
     (reset-db)
     (is (= (:total_rows (<! (core/all-docs db))) 0))
     (let [doc  {:name "David" :age 46}
           post-res (<! (core/post-doc db doc))
           put-res (<! (core/put-doc db (merge doc {:_id (:id post-res)
                                                    :_rev (:rev post-res)
                                                    :name "Bosse"})))
           get-res (<! (core/get-doc db (:id post-res)))]
       (is (= (:name get-res) "Bosse"))))

   (testing "reset DB works"
     (reset-db)
     (let [res (<! (core/all-docs db))]
       (is (= (:total_rows res) 0))))

   (testing "deleting document works"
     (reset-db)
     (let [doc {:name "Bo"}
           post-res (<! (core/post-doc db doc))
           get-res-1 (<! (core/get-doc db (:id post-res)))
           _ (is (= (:name get-res-1) "Bo"))
           rem-res (<! (core/remove-doc db get-res-1))
           get-res-2 (<! (core/get-doc db (:id post-res)))]
       (is (:error get-res-2))))

   (testing "deleting document with wrong rev fails"
     (reset-db)
     (let [doc {:name "Bo"}
           post-res (<! (core/post-doc db doc))
           del-res (<! (core/remove-doc db {:_id (:id post-res) :_rev "foo"}))]
       (is (:error del-res))))

   (testing "bulk insertion works"
     (reset-db)
     (let [docs [{:name "Bo"} {:name "Nisse"}]
           post-res (<! (core/bulk-docs db docs))]
       (is (not (:error post-res)))
       (is (= (:total_rows (<! (core/all-docs db))) 2))))

   (testing "adding attachment works"
     (reset-db)
     (let [doc {:name "Bo"}
           post-res (<! (core/put-doc db doc))
           att-res (<! (core/put-attachment db (:id post-res) "myatt" (:rev post-res) "My Blob" "text/plain"))]
       (is (not (:error att-res)))
       (is (= (type (<! (core/get-attachment db (:id post-res) "myatt"))) js/Blob))))

   (testing "querying one document on a field using local function"
     (reset-db)
     (let [doc {:name "Bo"}
           doc2 {:name "Dave"}
           put-res (<! (core/bulk-docs db [doc doc2]))
           query-res (<! (core/query db (js* "function (doc) { if (doc.name == 'Bo') emit(doc.name, doc); }")  {:reduce false}))
           _ (print "query result is " query-res)
           row (first (:rows query-res))]
       (is (:rows query-res))
       (is (= (count (:rows query-res)) 1))
       (is (= (:key row) "Bo"))))
   
   (done))))
