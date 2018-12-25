(ns reagent-sandbox.views
  (:require
   [reagent.core :as r]
   [reagent-sandbox.actions :as actions]
   [reagent-sandbox.constants :as constants]
   [reagent-sandbox.store :as store]
   [reagent-sandbox.leaflet-map :as leaflet-map]))

(defn navigation-bar
  []
  [:header.navbar.bg-primary.p-2.navigation-bar
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

(defn attributes-table
  []
  (let [attributes (into (sorted-map)
                         (dissoc (get @store/data-cursor "Parcel")
                                 "Shape" "SimpleShape" "BrtWebsite"))
        create-row (fn [[attr val]]
                     ^{:key attr} [:tr
                                   [:td attr]
                                   [:td val]])]
    [:aside.table-container.p-2
      [:table.table.attribute-table
        [:thead
          [:tr
          [:th "Attribute"]
          [:th "Value"]]]
       [:tbody
        (doall
         (map create-row attributes))]]]))

(defn main
  []
  [:div.main-container
   [leaflet-map/leaflet-map {:parcel-data @store/data-cursor}]
   [:div.sidebar-container
    (cond
      (nil? @store/data-cursor) [no-data]
      :else [attributes-table])]])

(defn settings
  []
  [:div.settings-form-container.p-2
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
