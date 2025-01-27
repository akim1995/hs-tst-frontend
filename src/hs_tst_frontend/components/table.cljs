(ns hs-tst-frontend.components.table
  (:require [helix.core :refer [defnc <>]]
            [helix.dom :as d]))

(defnc Table
  [{:keys [patients on-patient-select]}]
  (d/div {:class "overflow-x-auto rounded-b-lg border border-gray-200"}
         (d/table {:class "min-w-full divide-y-2 divide-gray-200 bg-white text-sm"}
                  (d/thead {:class "text-left"}
                           (d/tr
                            (let [headers ["Name" "Gender" "Date of Birth" "Address" "Insurance Number"]]
                              (for [header headers]
                                (d/th {:key header :class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"}
                                      header)))))
                  (d/tbody {:class "divide-y divide-gray-200"}
                           (for [patient patients]
                             (d/tr {:on-click #(on-patient-select (:id patient)) :key (:id patient) :class "hover:bg-blue-50 odd:bg-gray-50 cursor-pointer"}
                                   (d/td {:class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"} (:name patient))
                                   (d/td {:class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"} (:gender patient))
                                   (d/td {:class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"} (:date-of-birth patient))
                                   (d/td {:class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"} (:address patient))
                                   (d/td {:class "whitespace-nowrap px-4 py-2 font-medium text-gray-900"} (:health-insurance-number patient))))))))
