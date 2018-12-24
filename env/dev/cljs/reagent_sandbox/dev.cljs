(ns ^:figwheel-no-load reagent-sandbox.dev
  (:require
    [reagent-sandbox.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
