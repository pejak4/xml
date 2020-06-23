package com.example.demo.security;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Collections;
import java.util.EnumSet;

@Component
public class Acl {
    public void addRestorePermissionsAcl(String role) throws IOException
    {
        String username = "Jovan";
        Path file = Paths.get("./src/main/resources/application.properties");//C://Users/jovan/OneDrive/Desktop/ss/a.txt

        AclFileAttributeView aclAttr = Files.getFileAttributeView(file, AclFileAttributeView.class);

        UserPrincipalLookupService currULS = file.getFileSystem().getUserPrincipalLookupService();
        UserPrincipal principal = currULS.lookupPrincipalByName(username);

        AclEntry.Builder builder = AclEntry.newBuilder();
        if(role.equals("ADMIN")) {
            builder.setPermissions(EnumSet.of(
                    AclEntryPermission.READ_DATA, // mozemo da citamo fajl tj otvorimo
                    AclEntryPermission.READ_ACL,
                    AclEntryPermission.READ_ATTRIBUTES,
                    AclEntryPermission.READ_NAMED_ATTRS,
                    AclEntryPermission.SYNCHRONIZE,
                    AclEntryPermission.DELETE, // Sa ovim mozemo da brisemo, bez ovoga samo admin moze da brise
                    AclEntryPermission.ADD_FILE,
                    AclEntryPermission.APPEND_DATA,
                    AclEntryPermission.WRITE_ATTRIBUTES,
                    AclEntryPermission.ADD_SUBDIRECTORY,
                    AclEntryPermission.DELETE_CHILD,
                    AclEntryPermission.WRITE_ACL,
                    AclEntryPermission.WRITE_NAMED_ATTRS,
                    AclEntryPermission.WRITE_DATA,
                    AclEntryPermission.LIST_DIRECTORY));
        } else {
            builder.setPermissions(); // Ne mozemo da citamo fajl tj otvorimo
//                    AclEntryPermission.READ_ACL,
//                    AclEntryPermission.READ_ATTRIBUTES,
//                    AclEntryPermission.READ_NAMED_ATTRS,
//                    AclEntryPermission.SYNCHRONIZE,
//                    AclEntryPermission.DELETE, // Sa ovim mozemo da brisemo
//                    AclEntryPermission.ADD_FILE,
//                    AclEntryPermission.APPEND_DATA));
        }

        builder.setPrincipal(principal);
        builder.setType(AclEntryType.ALLOW);
        aclAttr.setAcl(Collections.singletonList(builder.build()));


        for(AclEntry entry : aclAttr.getAcl()) {
            System.out.println("---------------------------------");
            System.out.println(entry);
            System.out.println("---------------------------------");

            System.out.println("---------------------------------");
            System.out.println(entry.principal());
            System.out.println("---------------------------------");

            if(entry.principal().equals(principal)) {
                System.out.println("=== flags ===");
                for (AclEntryFlag flags : entry.flags()) {
                    System.out.println(flags.name());
                }

                System.out.println("=== permissions ===");
                for (AclEntryPermission permission : entry.permissions()) {
                    System.out.println(permission.name());
                }
            }
        }
    }
}
