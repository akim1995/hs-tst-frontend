(ns hs-tst-frontend.pages.patients-test
  (:require [hs-tst-frontend.pages.patients :as sut]
            [cljs.test :as t :include-macros true]))

(t/deftest test-filter-patients-by-search-text
  (t/testing "search by name"
    (let [patients [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]]
      (t/is (= (sut/filter-patients-by-search-text "john" [:name] patients)
               [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}]))))

  (t/testing "search by address"
    (let [patients [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]]
      (t/is (= (sut/filter-patients-by-search-text "456" [:address] patients)
               [{:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]))))

  (t/testing "search by multiple fields"
    (let [patients [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]]
      (t/is (= (sut/filter-patients-by-search-text "456" [:address :name] patients)
               [{:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]))))

  (t/testing "case insensitivity"
    (let [patients [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]]
      (t/is (= (sut/filter-patients-by-search-text "jANE" [:name] patients)
               [{:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]))))

  (t/testing "handling nil values in fields"
    (let [patients [{:name "John Doe" :address nil :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number nil}]]
      (t/is (= (sut/filter-patients-by-search-text "456" [:address :name] patients)
               [{:name "Jane Smith" :address "456 Oak St" :health-insurance-number nil}]))))

  (t/testing "empty search text"
    (let [patients [{:name "John Doe" :address "123 Elm St" :health-insurance-number "xxxx-xxxx-xxxx"}
                    {:name "Jane Smith" :address "456 Oak St" :health-insurance-number "1234-1234-1234-1234"}]]
      (t/is (= (sut/filter-patients-by-search-text "" [:name] patients)
               patients)))))

(comment
  (cljs.test/run-tests 'hs-tst-frontend.pages.patients-test))
