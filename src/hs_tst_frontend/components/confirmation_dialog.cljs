(ns hs-tst-frontend.components.confirmation-dialog
  (:require [helix.core :refer [$ defnc]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc ConfirmationDialog [{:keys [is-open? on-close on-delete title text]}]
  (let [modal-ref (hooks/use-ref nil)]
    (hooks/use-effect
      [is-open?]
      (if is-open?
        (.showModal (.-current modal-ref))
        (.close (.-current modal-ref))))

    (d/dialog {:ref modal-ref}
              (d/div {:class "rounded-lg bg-white p-8 shadow-2xl"}
                     (d/h2 {:class "text-lg font-bold"} title)
                     (d/p {:class "mt-2 text-md text-gray-500"} text)
                     (d/div {:class "mt-4 flex justify-end gap-2"} ;; align right is needed
                            (d/button {:on-click on-close
                                       :class "cursor-pointer rounded bg-gray-50 px-4 py-2 text-sm font-medium text-gray-600"}
                                      "Cancel")
                            (d/button {:on-click on-delete :class "cursor-pointer rounded bg-red-50 px-4 py-2 text-sm font-medium text-red-600"}
                                      "Delete"))))))
