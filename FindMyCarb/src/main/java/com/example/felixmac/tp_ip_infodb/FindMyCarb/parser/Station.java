package com.example.felixmac.tp_ip_infodb.FindMyCarb.parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Objects;

public class Station implements Parcelable {
    private String adresse = "";
    private Float gazole = Float.parseFloat("0");
    private Float SP95 = Float.parseFloat("0");
    private Float SP98 = Float.parseFloat("0");
    private Float E10 = Float.parseFloat("0");
    private Float E85 = Float.parseFloat("0");
    private Float GPLc = Float.parseFloat("0");
    private Float distance_user;

    /**
     * Constructeur Basic
     */
    public Station() {
    }

    /**
     * Constructeur Complet
     * @param adresse
     * @param gazole
     * @param sp95
     * @param sp98
     * @param e10
     * @param e85
     * @param gpLc
     */
    public Station(String adresse, Float gazole, Float sp95, Float sp98, Float e10, Float e85, Float gpLc) {
        this.adresse = adresse;
        this.gazole = gazole;
        this.SP95 = sp95;
        this.SP98 = sp98;
        this.E10 = e10;
        this.E85 = e85;
        this.GPLc = gpLc;
    }

    /**
     *
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     *
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @return
     */
    public Float getGazole() {
        return gazole;
    }

    /**
     *
     * @param gazole
     */
    public void setGazole(String gazole) {
        this.gazole = Float.parseFloat(gazole);
    }

    /**
     *
     * @return
     */
    public Float getSP95() {
        return SP95;
    }

    /**
     *
     * @param SP95
     */
    public void setSP95(String SP95) {
        this.SP95 = Float.parseFloat(SP95);
    }

    /**
     *
     * @return
     */
    public Float getSP98() {
        return SP98;
    }

    /**
     *
     * @param SP98
     */
    public void setSP98(String SP98) {
        this.SP98 = Float.parseFloat(SP98);
    }

    /**
     *
     * @return
     */
    public Float getE10() {
        return E10;
    }

    /**
     *
     * @param e10
     */
    public void setE10(String e10) { E10 = Float.parseFloat(e10);}

    /**
     *
     * @return
     */
    public Float getE85() {
        return E85;
    }

    /**
     *
     * @param e85
     */
    public void setE85(String e85) {
        E85 = Float.parseFloat(e85);
    }

    /**
     *
     * @return
     */
    public Float getGPLc() {
        return GPLc;
    }

    /**
     *
     * @param GPLc
     */
    public void setGPLc(String GPLc) {
        this.GPLc = Float.parseFloat(GPLc);
    }

    /**
     *
     * @return
     */
    public Float getDistance_user() {
        return distance_user;
    }

    /**
     *
     * @param distance_user
     */
    public void setDistance_user(Float distance_user) {
        this.distance_user = distance_user;
    }

    // Comparators needed to sort the station list
    /**
     * Comparateur Gazole
     */
    public static Comparator<Station> GazoleComparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getGazole();
            Float carb2 = t2.getGazole();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur SP95
     */
    public static Comparator<Station> SP95Comparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getSP95();
            Float carb2 = t2.getSP95();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur SP98
     */
    public static Comparator<Station> SP98Comparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getSP98();
            Float carb2 = t2.getSP98();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur E10
     */
    public static Comparator<Station> E10Comparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getE10();
            Float carb2 = t2.getE10();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur E85
     */
    public static Comparator<Station> E85Comparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getE85();
            Float carb2 = t2.getE85();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur GPLc
     */
    public static Comparator<Station> GPLcComparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float carb1 = t1.getGPLc();
            Float carb2 = t2.getGPLc();
            if (carb1.equals(0.0f)){
                carb1 = 3.0f;
            }
            if (carb2.equals(0.0f)){
                carb2 = 3.0f;
            }
            //ascending order
            return carb1.compareTo(carb2);
        }
    };
    /**
     * Comparateur Distance
     */
    public static Comparator<Station> distance_userComparator = new Comparator<Station>() {

        @Override
        public int compare(Station t1, Station t2) {
            Float distance_user = t1.getDistance_user();
            Float distance_user1 = t2.getDistance_user();

            //ascending order
            if (distance_user == null) distance_user = 2000f;
            if (distance_user1 == null) distance_user1 = 2000f;

            return Objects.requireNonNull(distance_user).compareTo(distance_user1);
        }
    };

    /**
     * Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adresse);
        dest.writeValue(this.gazole);
        dest.writeValue(this.SP95);
        dest.writeValue(this.SP98);
        dest.writeValue(this.E10);
        dest.writeValue(this.E85);
        dest.writeValue(this.GPLc);
        dest.writeValue(this.distance_user);
    }

    /**
     * Parcelable
     * @param in
     */
    protected Station(Parcel in) {
        this.adresse = in.readString();
        this.gazole = (Float) in.readValue(Float.class.getClassLoader());
        this.SP95 = (Float) in.readValue(Float.class.getClassLoader());
        this.SP98 = (Float) in.readValue(Float.class.getClassLoader());
        this.E10 = (Float) in.readValue(Float.class.getClassLoader());
        this.E85 = (Float) in.readValue(Float.class.getClassLoader());
        this.GPLc = (Float) in.readValue(Float.class.getClassLoader());
        this.distance_user = (Float) in.readValue(Float.class.getClassLoader());
    }

    /**
     * Parcelable
     */
    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel source) {
            return new Station(source);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
}
