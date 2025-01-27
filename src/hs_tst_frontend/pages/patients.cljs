(ns hs-tst-frontend.pages.patients
  (:require [helix.core :refer [defnc $]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            [hs-tst-frontend.components.table :refer [Table]]
            [hs-tst-frontend.format-patient :refer [format-patient]]
            [hs-tst-frontend.components.patient-page-header :refer [PatientPageHeader]]
            [hs-tst-frontend.hooks.use-get-patients :refer [use-get-patients]]
            [clojure.string :as s]
            ["react-router-dom" :refer [useNavigate]]))

(defn filter-patients-by-search-text
  [search-text search-by-fields patients]
  (let [search-text-lower (s/lower-case search-text)]
    (filter (fn [patient]
              (some (fn [field]
                      (let [field-value (patient field)]
                        (s/includes?
                         ((fnil s/lower-case "") ;; fnil is to get default value in case field is nil
                          field-value)
                         search-text-lower))) search-by-fields))
            patients)))

(def searchable-fields #{:name :address :health-insurance-number})

(defnc Patients []
  (let [{:keys [is-loading? is-success? is-error? data]} (use-get-patients)
        [search-text set-search-text] (hooks/use-state "")
        filtered-padients (filter-patients-by-search-text search-text searchable-fields data)
        navigate (useNavigate)
        search-text-change-hdlr (fn [e] (set-search-text (.. e -target -value)))]

    (d/div {:class "container mx-auto px-4 py-8"}
           ($ PatientPageHeader {:value search-text :on-change search-text-change-hdlr})
           (cond
             is-loading? "loading"
             is-error? "Request to get patients failed"
             is-success? ($ Table
                            {:patients (map format-patient filtered-padients) :on-patient-select (fn [id] (navigate id))})))))
