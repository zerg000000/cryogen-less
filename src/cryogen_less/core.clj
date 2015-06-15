(ns cryogen-less.core
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clojure.string :as s]
            [cryogen-core.compiler :as cc]
            [text-decoration.core :refer :all])
  (:import [org.lesscss LessCompiler]))

(defn- compile-file
  [compiler src dest]
  (if (fs/exists? src)
    (do
    (println "\t-->" (cyan dest))
    (.compile compiler src (io/file (str cc/public dest))))))

(defn compile-less
  "Compiles all the less into css and spits them out into the public folder"
  [config]
  (let [theme (:theme config)
        compiler (LessCompiler.
                  (or (:less-opts config)
                      ["--relative-urls" "--strict-math=on"]))
        less-assets (for [src (:less-src config)
                          path [(str "resources/templates/themes/" theme "/")
                                 (str "resources/templates/")]]
                       (io/file (str path src)))]
    (println (blue "compile less assets"))
    (dorun (map #(compile-file compiler
                               % (str "/css/"
                                      (fs/base-name % true)
                                      ".css")) less-assets))
    ))

(defn init []
  "do nothing")
