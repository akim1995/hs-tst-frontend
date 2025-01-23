(ns hs-tst-frontend.pages.patient
  (:require [helix.core :refer [defnc $ <>]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [hs-tst-frontend.components.patient-card :refer [PatientCard]]
            ["react-router-dom" :refer [useParams]]))

(defn fetch-data [patient-id]
  (-> (js/fetch (str "http://localhost:8000/patient/" patient-id))
      (.then (fn [response]
               (.json response)))
      (.then (fn [data]
               (js->clj data :keywordize-keys true)))))

(defnc Patient []
  (let [params (js->clj (useParams))
        patient-id (get params "patient-id")
        [patient-data set-patient] (hooks/use-state nil)]
    (hooks/use-effect
      :once
      (.then (fetch-data patient-id) (fn [value] (set-patient value))))
    (d/div {:class "container mx-auto px-4 py-8"} ($ PatientCard {:patient patient-data}))))
