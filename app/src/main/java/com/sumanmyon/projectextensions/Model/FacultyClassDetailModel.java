package com.sumanmyon.projectextensions.Model;

import com.sumanmyon.projectextensions.Model.BaseModel;

import java.util.List;

public class FacultyClassDetailModel extends BaseModel {
    public List<Data> Data ;

    public class Data{
        public String FacultyCode;
        public String FacultyName;
        public List<ClassSectionSubDetail> ClassSectionSubDetail;
    }

    public class ClassSectionSubDetail
    {
        public String ClassCode;
        public String ClassName;
        public List<SectionList> SectionList;
        public List<SubjectList> SubjectList;
    }

    public class SectionList {
        public String SectionCode;
        public String SectionName;
    }

    public class SubjectList{
        public String SubjectCode;
        public String SubjectName;
    }
}
