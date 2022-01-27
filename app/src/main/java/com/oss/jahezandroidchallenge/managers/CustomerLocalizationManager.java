package com.oss.jahezandroidchallenge.managers;


public class CustomerLocalizationManager {

    public interface OnChangingLanguage {
        void onChanging();
    }

    private OnChangingLanguage mOnChangingLanguage;
    private static volatile CustomerLocalizationManager uniqueInstance;

    private CustomerLocalizationManager(OnChangingLanguage onChangingLanguage) {
        mOnChangingLanguage = onChangingLanguage;
    }

    public static void initialize(OnChangingLanguage onChangingLanguage) {
        if (uniqueInstance == null) {
            synchronized (CustomerLocalizationManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new CustomerLocalizationManager(onChangingLanguage);
                }
            }
        }
    }

    public static CustomerLocalizationManager getInstance() {
        if (uniqueInstance == null) {
            throw new IllegalStateException(
                    CustomerLocalizationManager.class.getSimpleName() + " is not initialized, call initialize(onLogout) " +
                            "static method first");
        }
        return uniqueInstance;
    }


    public void setLanguage(String language) {
        SharedPrefsManager.getInstance().setString(SharedPrefKeys.LANGUAGE, language);
        mOnChangingLanguage.onChanging();
    }

}
