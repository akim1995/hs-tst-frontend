(ns hs-tst-frontend.format-patient)

(defn format-patient [patient]
  (-> patient
      (update :address #(if (or (nil? %) (empty? %)) "-" %))
      (update :gender #(get {"M" "Male" "F" "Female"} %))
      (update :date-of-birth #(.toLocaleDateString (js/Date. %)))))
