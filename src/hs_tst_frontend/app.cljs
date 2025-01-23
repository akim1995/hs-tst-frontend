(ns hs-tst-frontend.app
  (:require [helix.core :refer [defnc $ <>]]
            [helix.dom :as d]
            ["react-dom/client" :as rdom]
            ["react-router-dom" :refer [BrowserRouter Routes Route]]
            [hs-tst-frontend.pages.patients :refer [Patients]]
            [hs-tst-frontend.pages.create-patient :refer [CreatePatient]]
            [hs-tst-frontend.pages.patient :refer [Patient]]))

;; (def patients [{:id 1 :name "akim" } {:id 2 :name "katya"}])
;; NOTE latest version of react router (7.1.2) is not working with shadow-cljs


(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defnc app []
   ($ BrowserRouter {:future #js {"v7_startTransition" false "v7_relativeSplatPath" false}}
    ($ Routes
       ($ Route {:path "/" :element ($ Patients)}) ($ Route {:path "/create" :element ($ CreatePatient)})
       ($ Route {:path "/:patient-id" :element ($ Patient)}))))

(comment)

(defn ^:export init []
    (js/console.log "init!")
    (.render root ($ app)))

;; After hot reload this will run to rerender dom
(defn ^:dev/after-load reload []
  (.render root ($ app)))
