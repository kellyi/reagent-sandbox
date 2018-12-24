(ns reagent-sandbox.actions
  (:require
   [ajax.core :refer [POST]]
   [reagent-sandbox.constants :as constants]
   [reagent-sandbox.store :as store]))

(defn get-value [e] (-> e .-target .-value))

(defn reset-success-error
  []
  (do
    (reset! store/success-cursor nil)
    (reset! store/error-cursor nil)))

(defn handle-input-change
  [e]
  (do
    (reset-success-error)
    (reset! store/input-cursor (get-value e))))

(defn complete-submit-form
  []
  (do
    (reset! store/submit-form-cursor false)
    (reset! store/success-cursor "Success!")))

(defn fail-submit-form
  []
  (do
    (reset! store/submit-form-cursor false)
    (reset! store/error-cursor "Error!")))

(defn submit-form
  []
  (let [params {:key @store/input-cursor}]
    (do
      (reset-success-error)
      (reset! store/submit-form-cursor true)
      (POST constants/api-endpoint {:params params
                          :format :json
                          :handler complete-submit-form
                          :error-handler fail-submit-form}))))
