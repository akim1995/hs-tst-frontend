(ns hs-tst-frontend.components.patient-page-header
  (:require [helix.core :refer [defnc $]]
            ["react-router-dom" :refer [Link]]
            [helix.dom :as d]))

(defnc PatientPageHeader [{:keys [on-change value]}]
  (d/header {:class "flex items-center justify-between bg-gray-100 px-4 py-2 "}
            (d/div {:class "flex flex-1 max-w-md"}
                   (d/input {:type "text"
                             :value value
                             :on-change on-change
                             :placeholder "Search..."
                             :class "w-full bg-white px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-blue-300"}))
            (d/div {:class "ml-4"}
                   ($ Link {:to "/create" :className "px-6 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition"}"Create Patient"))))
