(ns reagent-sandbox.prod
  (:require
    [reagent-sandbox.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
