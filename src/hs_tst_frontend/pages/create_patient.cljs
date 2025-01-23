(ns hs-tst-frontend.pages.create-patient
  (:require [helix.core :refer [defnc $]]
            ["react-router-dom" :refer [Link]]
            [hs-tst-frontend.patient-http-requests :as patient-http-client]
            [helix.dom :as d]))

(def label-classes "block text-sm font-medium text-gray-700")
(def input-classes "border bg-white mt-1 block w-96 rounded-sm border-blue-500 shadow-sm sm:text-sm")

(defn on-submit-hdlr [e]
  (.preventDefault e)
  (let [form-data-obj (js/FormData. (.-target e))
        patient (into {} (for [field ["name" "gender" "date-of-birth" "health-insurance-number" "address"]]
                           [(keyword field) (.get form-data-obj field)]))]
    (patient-http-client/post-data (update patient :date-of-birth (fn [dob] (.toISOString (js/Date. dob)))))))
   ;; (js/console.log patient)))

(defnc CreatePatient []
  (d/div {:class "container mx-auto px-4 py-8"}
         (d/h1 {:class "text-2xl font-bold mb-4"} "Create Patient")
         (d/form {:on-submit on-submit-hdlr :class "space-y-4"}
                 (d/div (d/label {:for "name" :class label-classes} "Name *")
                        (d/input {:id "name" :required true :type "text" :name "name" :class input-classes}))
                 (d/div
                  (d/label {:for "gender" :class label-classes} "Gender *")
                  (d/select {:id "gender" :required true :name "gender" :class input-classes}
                            (d/option {:value "M"} "Male")
                            (d/option {:value "F"} "Female")))
                 (d/div
                  (d/label {:for "dob" :class label-classes} "Date of Birth *")
                  (d/input {:id "dob" :required true :name "date-of-birth" :type "date" :class input-classes}))

                 (d/div (d/label {:for "health-insurance-number" :class label-classes} "Health Insurance Number *")
                        (d/input {:id :required true "health-insurance-number" :placeholder "xxxx-xxxx-xxxx-xxxx" :type "text" :name "health-insurance-number" :class input-classes}))
                 (d/div
                  (d/label {:for "address" :class label-classes} "Address")
                  (d/textarea {:id "address" :name "address" :class input-classes}))
                 (d/div {:class "flex justify-end w-96 space-x-2"}
                        (d/button {:type "submit" :class "px-4 py-2 text-sm font-medium text-white bg-gray-600 rounded-md shadow-sm hover:bg-gray-700"} "Cancel")
                        (d/button {:type "submit" :class "px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700"} "Save")))))
