(ns hs-tst-frontend.components.patient-card
  (:require [helix.core :refer [defnc $ <>]]
            [helix.dom :as d]
            [hs-tst-frontend.format-patient :refer [format-patient]]))

(defnc PatientCardListItem [{:keys [label value]}]
  (d/dl {:class "-my-3 divide-y divide-gray-100 text-sm"}
        (d/div {:class "grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4"}
               (d/dt {:class "font-medium text-gray-900"} label)
               (d/dd {:class "text-gray-700 sm:col-span-2"} value))))

(defnc PatientCard [{:keys [patient]}]
  (let [{:keys [name gender date-of-birth health-insurance-number]} (format-patient patient)] ; Destructure `patient` map
    (<>
     (d/header {:class "flex items-center justify-between bg-gray-100 px-4 py-2"}
               (d/span {:class "font-bold"} name)
               (d/div {:class "ml-4"} (d/button {:class "px-6 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition"} "Edit")))

     (d/div {:class "flow-root rounded-b-lg border border-gray-100 py-3 shadow-sm"}
            ($ PatientCardListItem {:label "Gender" :value gender})
            ($ PatientCardListItem {:label "Date of Birth" :value date-of-birth})
            ($ PatientCardListItem {:label "Health Insurance Number" :value health-insurance-number})
            ($ PatientCardListItem)))))
