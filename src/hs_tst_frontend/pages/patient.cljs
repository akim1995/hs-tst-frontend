(ns hs-tst-frontend.pages.patient
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [hs-tst-frontend.components.patient-card :refer [PatientCard]]
            [hs-tst-frontend.hooks.use-get-patient :refer [use-get-patient]]
            ["react-router-dom" :refer [useParams]]))

(defnc Patient []
  (let [params (js->clj (useParams))
        patient-id (get params "patient-id")
        {:keys [is-loading? is-error? is-success? data refetch-data]} (use-get-patient patient-id)]
    (d/div {:class "container mx-auto px-4 py-8"}
           (cond
             is-loading? (d/div "Loading...")
             is-error? (d/div "Request to get patient failed")
             is-success? ($ PatientCard {:patient data :refetch-patient #(refetch-data)})))))
