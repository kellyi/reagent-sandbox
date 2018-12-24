(ns reagent-sandbox.store
  (:require
   [reagent.core :as r]
   [reagent-sandbox.constants :as constants]))

(def app-state (r/atom {:form-state
                        {:input-field ""
                         :submitting-form false
                         :data nil
                         :error-message nil}
                        :credentials
                        {:api-url ""}
                        :navigation
                        {:active-page (:main constants/pages)}}))

(def search-input-cursor (r/cursor app-state [:form-state :input-field]))
(def submit-form-cursor (r/cursor app-state [:form-state :submitting-form]))
(def data-cursor (r/cursor app-state [:form-state :data]))
(def error-cursor (r/cursor app-state [:form-state :error-message]))
(def active-page-cursor (r/cursor app-state [:navigaation :active-page]))
(def api-url-cursor (r/cursor app-state [:credentials :api-url]))
