package com.sumanmyon.projectextensions.Model;

import java.util.List;

public class HamroKishan {

    public static class Search{
        public List<Data> data ;

        public class Data{
            public String id;
            public String logo_image;
            public String name;
        }
    }


    public static class SocialMedia{
        public Data data ;
        public String message;

        public class Data{
            public String facebook;
            public String twitter;
            public String youtube;
            public String instagram;
        }
    }



}
