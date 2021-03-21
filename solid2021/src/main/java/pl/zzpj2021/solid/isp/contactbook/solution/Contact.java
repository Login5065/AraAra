package pl.zzpj2021.solid.isp.contactbook.solution;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
class Contact implements Dialler,Emailer {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String emailAddress;
    @Getter
    @Setter
    private String telephone;


}



