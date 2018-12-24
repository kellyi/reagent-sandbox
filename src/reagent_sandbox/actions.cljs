(ns reagent-sandbox.actions
  (:require
   [ajax.core :refer [GET]]
   [reagent-sandbox.constants :as constants]
   [reagent-sandbox.store :as store]))

(defn get-value [e] (-> e .-target .-value))

(defn reset-data-error
  []
  (do
    (reset! store/data-cursor nil)
    (reset! store/error-cursor nil)))

(defn handle-search-input-change
  [e]
  (do
    (reset-data-error)
    (reset! store/search-input-cursor (get-value e))))

(defn complete-submit-form
  [data]
  (do
    (println data)
    (reset! store/submit-form-cursor false)
    (reset! store/data-cursor data)))

(defn fail-submit-form
  [err]
  (do
    (.warn js/console err)
    (reset! store/submit-form-cursor false)
    (reset! store/error-cursor "Error!")))

(defn submit-form
  []
  (let [api-url (str @store/api-url-cursor "/" @store/search-input-cursor)]
    (do
      (reset-data-error)
      (reset! store/submit-form-cursor true)
      (GET api-url {:format :json
                    :handler complete-submit-form
                    :error-handler fail-submit-form}))))

(defn make-main-page-active
  []
  (reset! store/active-page-cursor (:main constants/pages)))

(defn make-settings-page-active
  []
  (reset! store/active-page-cursor (:settings constants/pages)))

(defn handle-api-url-input-change
  [e]
  (reset! store/api-url-cursor (get-value e)))
