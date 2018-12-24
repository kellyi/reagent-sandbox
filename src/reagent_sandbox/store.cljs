(ns reagent-sandbox.store
  (:require
   [reagent.core :as r]))

(def app-state (r/atom {:input-field ""
                        :submitting-form false
                        :success-message nil
                        :error-message nil}))

(def input-cursor (r/cursor app-state [:input-field]))
(def submit-form-cursor (r/cursor app-state [:submitting-form]))
(def success-cursor (r/cursor app-state [:success-message]))
(def error-cursor (r/cursor app-state [:error-message]))
