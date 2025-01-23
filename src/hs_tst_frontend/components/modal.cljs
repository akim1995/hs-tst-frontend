(ns hs-tst-frontend.components.modal
 (:require [helix.core :refer [defnc]]
           [helix.dom :as d]))


;; Header is needed with on-close callback
;; and width should be fixed

(defnc modal [{:keys [children is-open?]}]
  (when is-open?
   (d/dialog {:style {:min-width "700px"} :class "fixed border border-slate top-10 mx-auto shadow-md block animate-expand rounded-lg p-8"} children)))
