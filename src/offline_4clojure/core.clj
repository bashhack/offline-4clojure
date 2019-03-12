(ns offline-4clojure.core
  (:require [clj-http.client :as http]
            [clojure.string :as string]
            [cheshire.core :refer [parse-string]])
  (:use [clojure.pprint]))

(defn fetch-problem-data [n]
  (println "fetching" n)
  (parse-string
   (:body
    (http/get (str "http://www.4clojure.com/api/problem/" n)))))

(defn strip-cr [somestr]
  (string/replace somestr "\r" ""))

(defn get-tests [problem-data]
  (let [tests (problem-data "tests")]
    (strip-cr (string/join "\n" tests))))

(defn wrap-in-are [tests]
  (str "(defn -main []\n  (are [soln] soln\n"
       tests
       "\n))\n"))

(defn display-vector [title entries]
  (str title " - "
       (string/join ":" entries)))

(defn generate-ns [n]
  (str "(ns offline-4clojure.p" n "\n  (:use clojure.test))"))

(def template-soln "(def __\n;; your solution here\n)")

(defn make-comment [somestr]
  (str ";; "
       (string/replace somestr #"\n" "\n;;")))

(defn generate-file [n]
  (let [problem-data (fetch-problem-data n)]
    (string/join "\n"
                 [(make-comment (str
                                 (problem-data "title")
                                 " - "
                                 (problem-data "difficulty")))
                  (make-comment (strip-cr (problem-data "description")))
                  (make-comment (display-vector "tags"
                                                (problem-data "tags")))

                  (make-comment (display-vector "restricted"
                                                (problem-data "restricted")))
                  (generate-ns n)
                  ""
                  template-soln
                  ""
                  (wrap-in-are (get-tests problem-data))])))

(defn write-file [n]
  (println "writing" n)
  (spit (str "src/offline_4clojure/p" n ".clj")
        (generate-file n)))

(defn -main
  [& args]
  (let [warning-on-update (str "⚠ WARNING: Fetching problems "
                               "may overwrite existing file content -\n"
                               "do you wish to continue? (y/n) [n]\n"
                               ">> ")
        confirmation      (do (print warning-on-update) (flush) (read-line))]
    (when (= "y" (string/lower-case confirmation))
      (doseq [i (range 0 200)]
        (future (write-file i)))))
  (shutdown-agents))
