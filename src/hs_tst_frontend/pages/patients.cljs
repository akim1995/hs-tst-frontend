(ns hs-tst-frontend.pages.patients
  (:require [helix.core :refer [defnc $ <>]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [hs-tst-frontend.components.table :refer [Table]]
            [hs-tst-frontend.format-patient :refer [format-patient]]
            [hs-tst-frontend.components.patient-page-header :refer [PatientPageHeader]]
            ["react-router-dom" :refer [useNavigate]]))

(defn fetch-data []
  (-> (js/fetch "http://localhost:8000/patient")
      (.then (fn [response]
               (.json response)))
      (.then (fn [data]
               (js->clj data :keywordize-keys true)))))


(defnc Patients []
  (let [[patients set-patients] (hooks/use-state nil)
        navigate (useNavigate)]
    (hooks/use-effect
      :once
      (.then (fetch-data) (fn [value] (set-patients value))))
    (d/div {:class "container mx-auto px-4 py-8"}
           ($ PatientPageHeader)
           ($ Table
              {:patients (map format-patient patients) :on-patient-select (fn [id] (navigate id))}))))
