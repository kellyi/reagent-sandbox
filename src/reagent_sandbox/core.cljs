(ns reagent-sandbox.core
  (:require
    [reagent.core :as r]
    [reagent-sandbox.views :as views]))

(defn mount-root []
  (r/render [views/home-page] (.getElementById js/document "root")))

(defn init! []
  (mount-root))
