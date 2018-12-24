(ns reagent-sandbox.core
    (:require
     [reagent.core :as r]
     [ajax.core :refer [POST]]))

(def api-endpoint
  "https://wt-f71cc06ebbbaeb13ae5e4354e34cb0e6-0.sandbox.auth0-extend.com/simple-form")

(def app-state (r/atom {:input-field ""
                        :submitting-form false
                        :success-message nil
                        :error-message nil}))

(def input-cursor (r/cursor app-state [:input-field]))
(def submit-form-cursor (r/cursor app-state [:submitting-form]))
(def success-cursor (r/cursor app-state [:success-message]))
(def error-cursor (r/cursor app-state [:error-message]))

(defn get-value [e] (-> e .-target .-value))

(defn reset-success-error
  []
  (do
    (reset! success-cursor nil)
    (reset! error-cursor nil)))

(defn handle-input-change
  [e]
  (do
    (reset-success-error)
    (reset! input-cursor (get-value e))))

(defn complete-submit-form
  []
  (do
    (reset! submit-form-cursor false)
    (reset! success-cursor "Success!")))

(defn fail-submit-form
  []
  (do
    (reset! submit-form-cursor false)
    (reset! error-cursor "Error!")))

(defn submit-form
  []
  (let [params {:key @input-cursor}]
    (do
      (reset-success-error)
      (reset! submit-form-cursor true)
      (POST api-endpoint {:params params
                          :format :json
                          :handler complete-submit-form
                          :error-handler fail-submit-form}))))

(defn success-or-error-message
  []
  (cond
    (not (nil? @error-cursor)) [:p {:style {:color "red"}} @error-cursor]
    (not (nil? @success-cursor)) [:p {:style {:color "green"}} @success-cursor]
    :else nil))

(defn form []
  [:div
   [:input {:on-change handle-input-change :value @input-cursor
            :disabled @submit-form-cursor}]
    [:button {:on-click submit-form :disabled @submit-form-cursor} "Submit"]
    [success-or-error-message]])

(defn home-page []
  [:div
   [:h2 "Simple form"]
   [form]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
