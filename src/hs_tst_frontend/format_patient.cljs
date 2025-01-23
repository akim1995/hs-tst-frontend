(ns hs-tst-frontend.format-patient)

(defn format-patient [patient]
  (-> patient
      (update :address #(or % "-"))
      (update :gender #(get {"M" "Male" "F" "emale"} %))
      (update :date-of-birth #(.toLocaleDateString (js/Date. %)))))
