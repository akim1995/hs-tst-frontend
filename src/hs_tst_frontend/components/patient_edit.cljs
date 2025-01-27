(ns hs-tst-frontend.components.patient-edit
  (:require [helix.core :refer [defnc]]
            ["react-router-dom" :refer [useNavigate]]
            [hs-tst-frontend.patient-http-requests :refer [put-data]]
            [helix.dom :as d]))

(def label-classes "block text-sm font-medium text-gray-700")
(def input-classes "border bg-white mt-1 block w-96 rounded-sm border-blue-500 shadow-sm sm:text-sm")

(defn on-submit-hdlr [e]
  (.preventDefault e)
  (let [form-data-obj (js/FormData. (.-target e))
        patient (into {} (for [field ["name" "gender" "date-of-birth" "health-insurance-number" "address" "id"]]
                           [(keyword field) (.get form-data-obj field)]))]
    (put-data (update patient :date-of-birth (fn [dob] (.toISOString (js/Date. dob)))) (:id patient))))

(defnc PatientEdit [{:keys [name gender date-of-birth health-insurance-number address id on-close on-update]}]
  (let [navigate (useNavigate)]
    (d/div {:class "p-3"}
           (d/form {:on-submit #(.then (on-submit-hdlr %) on-update) :class "space-y-4"}
                   (d/input {:hidden true :name "id" :defaultValue id})
                   (d/div (d/label {:for "name" :class label-classes} "Name *"
                                   (d/input {:id "name" :defaultValue name :required true :type "text" :name "name" :class input-classes})))
                   (d/div
                    (d/label {:for "gender" :class label-classes} "Gender *")
                    (d/select {:id "gender" :defaultValue gender :required true :name "gender" :class input-classes}
                              (d/option {:value "M"} "Male")
                              (d/option {:value "F"} "Female")))
                   (d/div
                    (d/label {:for "dob" :class label-classes} "Date of Birth *")
                    (d/input {:id "dob" :defaultValue date-of-birth :required true :name "date-of-birth" :type "date" :class input-classes}))
                   (d/div (d/label {:for "health-insurance-number" :class label-classes} "Health Insurance Number *")
                          (d/input {:id "health-insurance-number" :defaultValue health-insurance-number :required true :placeholder "xxxx-xxxx-xxxx-xxxx" :type "text" :name "health-insurance-number" :class input-classes}))
                   (d/div
                    (d/label {:for "address" :class label-classes} "Address")
                    (d/textarea {:id "address" :defaultValue address :name "address" :class input-classes}))
                   (d/div {:class "flex justify-end w-96 space-x-2"}
                          (d/button {:on-click #(on-close) :type "button" :class "cursor-pointer px-4 py-2 text-sm font-medium text-white bg-gray-600 rounded-md shadow-sm hover:bg-gray-700"} "Cancel")
                          (d/button {:class "cursor-pointer px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700"} "Save"))))))
