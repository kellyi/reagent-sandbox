(ns reagent-sandbox.views
  (:require
   [reagent.core :as r]
   [reagent-sandbox.actions :as actions]
   [reagent-sandbox.store :as store]))

(defn success-or-error-message
  []
  (cond
    (not (nil? @store/error-cursor)) [:p {:style {:color "red"}} @store/error-cursor]
    (not (nil? @store/success-cursor)) [:p {:style {:color "green"}} @store/success-cursor]
    :else nil))

(defn form []
  [:div
   [:input {:on-change actions/handle-input-change :value @store/input-cursor
            :disabled @store/submit-form-cursor}]
   [:button {:on-click actions/submit-form :disabled @store/submit-form-cursor} "Submit"]
   [success-or-error-message]])

(defn home-page []
  [:div
   [:h2 "Simple form"]
   [form]])
