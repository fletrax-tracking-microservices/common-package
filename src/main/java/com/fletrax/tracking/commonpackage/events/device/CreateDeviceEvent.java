package com.fletrax.tracking.commonpackage.events.device;

import com.fletrax.tracking.commonpackage.events.Event;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDeviceEvent implements Event
{
    private String ident;
}