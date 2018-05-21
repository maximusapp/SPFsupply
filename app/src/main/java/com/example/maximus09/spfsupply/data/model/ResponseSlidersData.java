package com.example.maximus09.spfsupply.data.model;


import java.util.List;

public class ResponseSlidersData {
    private Boolean success;
    private List<SlidersData>  sliders_data;

    public ResponseSlidersData(Boolean success, List<SlidersData> sliders_data) {
        this.success = success;
        this.sliders_data = sliders_data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<SlidersData> getSliders_data() {
        return sliders_data;
    }

    public void setSliders_data(List<SlidersData> sliders_data) {
        this.sliders_data = sliders_data;
    }

    public class SlidersData {
        public String company_name;
        public String logo;
        public String is_slider;
        public String id;

        public SlidersData(String company_name, String logo, String is_slider, String id) {
            this.company_name = company_name;
            this.logo = logo;
            this.is_slider = is_slider;
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getIs_slider() {
            return is_slider;
        }

        public void setIs_slider(String is_slider) {
            this.is_slider = is_slider;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
