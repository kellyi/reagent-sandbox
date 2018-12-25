(ns reagent-sandbox.leaflet-map
  (:require
   [reagent.core :as r]
   [reagent-sandbox.constants :as constants]))

(def leaflet-map-id "map")
(def leaflet-map-ref (r/atom nil))
(def leaflet-polygon-ref (r/atom nil))

(defn set-leaflet-map-view
  [lat lng zoom]
  (cond
    (nil? lat) nil
    (nil? lng) nil
    (nil? @leaflet-map-ref) nil
    :else (.setView @leaflet-map-ref #js [lat lng] zoom)))

(defn convert-shape-string-to-coordinates
  [input]
  (as-> input shape-string
    (clojure.string/replace shape-string "POLYGON ((" "")
    (clojure.string/replace shape-string "))" "")
    (clojure.string/split shape-string #", ")
    (map (fn [s]
           (vec (map #(cljs.reader/read-string %)
                     (reverse (clojure.string/split s #" "))))) shape-string)
    (vec shape-string)))

(defn create-polygon
  [shape]
  (let [polygon-coordinates (convert-shape-string-to-coordinates shape)
        leaflet-polygon (.polygon js/L (clj->js polygon-coordinates))]
    (reset! leaflet-polygon-ref leaflet-polygon)
    @leaflet-polygon-ref))

(defn add-polygon
  [shape]
  (.addTo (create-polygon shape) @leaflet-map-ref))

(defn remove-polygon
  []
  (cond
    (nil? @leaflet-polygon-ref) nil
    (nil? @leaflet-map-ref) nil
    (.hasLayer @leaflet-map-ref @leaflet-polygon-ref) (do (.removeLayer @leaflet-map-ref @leaflet-polygon-ref)
                                                          (reset! leaflet-polygon-ref nil))
    :else nil))

(defn leaflet-map-did-mount
  []
  (let [leaflet-map-component (.map js/L leaflet-map-id)]
    (do
      (reset! leaflet-map-ref leaflet-map-component)
      (set-leaflet-map-view 39.9524 -75.1636 15)
      (.addTo (.tileLayer js/L constants/basemap-url
                          (clj->js {:attribution constants/basemap-attribution
                                    :maxZoom 19})) @leaflet-map-ref))))

(def lng "X")
(def lat "Y")
(def parcel "Parcel")
(def shape "Shape")

(defn leaflet-map-did-update
  [this prev-props]
  (let [data (:parcel-data (r/props this))
        lng (get data lng)
        lat (get data lat)]
    (cond
      (nil? data) (remove-polygon)
      :else (let [shape (get (get data parcel) shape)]
              (do
                (set-leaflet-map-view lat lng 19)
                (remove-polygon)
                (add-polygon shape))))))

(defn leaflet-map-render
  []
  [:div.map-container {:id leaflet-map-id}])

(defn leaflet-map
  []
  (r/create-class {:reagent-render leaflet-map-render
                   :component-did-mount leaflet-map-did-mount
                   :component-did-update leaflet-map-did-update}))
