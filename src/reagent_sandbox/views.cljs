(ns reagent-sandbox.views
  (:require
   [reagent.core :as r]
   [reagent-sandbox.actions :as actions]
   [reagent-sandbox.constants :as constants]
   [reagent-sandbox.store :as store]))

(defn navigation-bar
  []
  [:header.navbar.bg-primary.p-2
   [:section.navbar-section
    [:button.btn.btn-link.text-secondary.m-1
     {:on-click actions/make-main-page-active}
     "Main"]
    [:button.btn.btn-link.text-secondary.m-1
     {:on-click actions/make-settings-page-active}
     "Settings"]]
   [:section.navbar-section
    [:input.form-input.m-1
     {:type "text" :on-change actions/handle-search-input-change :value @store/search-input-cursor
      :disabled @store/submit-form-cursor}]
    [:button.btn.m-1
     {:on-click actions/submit-form :disabled @store/submit-form-cursor}
     [:i.icon.icon-search]]]])

(defn no-data
  []
  [:div.empty.no-data
   [:p.empty-title.h5 "No data to display"]
   [:p.empty-subtitle "Try searching for some data"]])

(defn main
  []
  (cond
    (nil? @store/data-cursor) [no-data]
    :else [:h2 "main"]))

(defn settings
  []
  [:div.settings-form-container
   [:h2.settings-form-header "Settings"]
   [:div.form-group.settings-form-group
    [:label.form-label {:for "api-url-input"} "API URL"]
    [:input.form-input {:type "text" :value @store/api-url-cursor
                        :on-change actions/handle-api-url-input-change}]]])

(defn home-page []
  [:main
   [navigation-bar]
   (cond
     (= (:settings constants/pages) @store/active-page-cursor) [settings]
     :else [main])])
