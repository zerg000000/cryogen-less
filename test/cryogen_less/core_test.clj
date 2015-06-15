(ns cryogen-less.core-test
  (:require [clojure.test :refer :all]
            [cryogen-less.core :refer :all]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn copy-fixtures []
  (fs/copy-dir "fixtures/less" "resources/templates/themes/default/"))

(defn delete-fixtures []
  (fs/delete-dir "resources/templates"))

(defn delete-css []
  (fs/delete-dir "resources/public"))

(deftest bootstrap-test
  (testing "try to compile bootstrap as testsuite"
    (let [config {:theme "default"
                  :less-src ["theme.less" "bootstrap.less"]
                  :less-opts ["--relative-urls" "--strict-math=on"]}]
    (copy-fixtures)
    (compile-less config)
    (is (fs/exists? (io/file "resources/public/css/theme.css")))
    (is (fs/exists? (io/file "resources/public/css/bootstrap.css")))
    (delete-fixtures)
    (delete-css))))
