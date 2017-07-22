(ns wiseyak-site-generator.core
  (:gen-class)
  (:use [hiccup.core]
        [hiccup.page :only [html5]])
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]))

(defn prepare-output-dir
  "Prepare directory structure for generation"
  []
  (println "Preparing directory structure ...")
  (let [output-structure ["site/styles" "site/images"]]
    (map fs/mkdirs output-structure)))

(defn copy-resources
  "Copy site resources such as styles and scripts"
  []
  (println "Copying site resources ...")
  (do (fs/delete-dir "site/styles")
      (fs/delete-dir "site/images")
      (fs/copy-dir "resources/resource/images" "site/images")
      (fs/copy-dir "resources/resource/styles" "site/styles")))

(defn render-hiccup [hiccup-data]
  (html (eval hiccup-data)))

(defn file-text [file]
  (-> (io/resource file)
      (slurp)
      (read-string)
      (render-hiccup)))

(defn generate []
  (->> (file-text "template/base.clj")
       (spit "site/index.html")))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
