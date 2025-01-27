(ns hs-tst-frontend.components.patient-card
  (:require [helix.core :refer [defnc $ <>]]
            [helix.dom :as d]
            ["react-router-dom" :refer [Link useNavigate]]
            [hs-tst-frontend.patient-http-requests :as patient-req]
            [hs-tst-frontend.format-patient :refer [format-patient]]
            [hs-tst-frontend.components.confirmation-dialog :refer [ConfirmationDialog]]
            [hs-tst-frontend.components.patient-edit :refer [PatientEdit]]
            [clojure.string :as str]
            [helix.hooks :as hooks]))

(defnc PatientCardListItem [{:keys [label value]}]
  (d/dl {:class "-my-3 divide-y divide-gray-100 text-sm"}
        (d/div {:class "grid grid-cols-1 gap-1 p-3 sm:grid-cols-3 sm:gap-4"}
               (d/dt {:class "font-medium text-gray-900"} label)
               (d/dd {:class "text-gray-700 sm:col-span-2"} value))))

(defnc PatienView [{:keys [gender date-of-birth health-insurance-number address]}]
  (<>
   ($ PatientCardListItem {:label "Gender" :value gender})
   ($ PatientCardListItem {:label "Date of Birth" :value date-of-birth})
   ($ PatientCardListItem {:label "Health Insurance Number" :value health-insurance-number})
   ($ PatientCardListItem {:label "Address" :value address})))

(defnc PatientCard [{:keys [patient refetch-patient]}]
  (let [navigate (useNavigate)
        [patient-in-edit-state? set-patient-in-edit-state] (hooks/use-state false)
        [is-modal-open? set-is-modal-open] (hooks/use-state false)
        {:keys [name gender date-of-birth health-insurance-number address]} (format-patient patient)]
    (<>
     (d/header {:class "flex items-center justify-between bg-gray-100 px-4 py-2"}
               (d/span {:class "font-bold"} (if patient-in-edit-state? "Edit" name))
               (d/div {:class "flex items-center space-x-2 ml-4"}
                      ($ Link {:to "/"}
                         (d/button
                          {:title "Go back"
                           :class "cursor-pointer px-3 py-1 bg-gray-300 text-gray-700 hover:outline-1 rounded-md transition-all shadow-sm flex items-center"}
                          (d/i {:class "fa-solid fa-circle-left mr-2"})
                          "Back"))
                      (d/button
                       {:on-click #(set-patient-in-edit-state (not patient-in-edit-state?))
                        :title "Edit"
                        :class "cursor-pointer px-3 py-1 bg-gray-300 text-gray-700 hover:outline-1 rounded-md transition-all shadow-sm flex items-center"}
                       (d/i {:class "fa-solid fa-file-pen mr-2"})
                       "Edit")
                      (d/button
                       {:title "Delete"
                        :class "cursor-pointer px-3 py-1 bg-gray-300 text-gray-700 hover:outline-1 rounded-md transition-all shadow-sm flex items-center"
                        :on-click #(set-is-modal-open true)}
                       (d/i {:class "fa-solid fa-trash mr-2"})
                       "Delete")))

     (d/div {:class "rounded-lg border border-gray-100 py-3 shadow-sm"}
            (if patient-in-edit-state?
              ($ PatientEdit {:on-close #(set-patient-in-edit-state false)
                              :on-update #(do
                                            (set-patient-in-edit-state false)
                                            (refetch-patient))

                              :name name
                              :gender (:gender patient)
                              :id (:id patient)
                              :health-insurance-number (:health-insurance-number patient)
                              :date-of-birth (first (str/split (:date-of-birth patient) #"T"))
                              :address (:address patient)})

              ($ PatienView {:gender gender :date-of-birth date-of-birth :health-insurance-number health-insurance-number :address address}))
            ($ ConfirmationDialog {:is-open? is-modal-open?
                                   :on-close #(set-is-modal-open false)
                                   :on-delete (fn [] (-> (patient-req/delete-patient (:id patient))
                                                         (.then #(navigate "/"))))
                                   :title "Are you sure you want to delete the patient?"
                                   :text "This action is irreversable"})))))
