package wendydeluca.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wendydeluca.u5d11.entities.Device;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.BadRequestException;
import wendydeluca.u5d11.payloads.DeviceDTO;
import wendydeluca.u5d11.payloads.UserDTO;
import wendydeluca.u5d11.services.DeviceService;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    public DeviceService deviceService;



    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy){
        return deviceService.getAllDevices(page,size,sortBy);
    }

    @PostMapping //SAVE
    @ResponseStatus(HttpStatus.CREATED) //STATUS 201 OK
    public Device saveDevice(@RequestBody @Validated DeviceDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceService.saveDevice(body);
    }

    @GetMapping("/{deviceId}") //GET BY ID
    public Device findDeviceById(@PathVariable UUID deviceId){
        return deviceService.getDeviceById(deviceId);

    }

    @PutMapping("/{deviceId}") // UPDATING 1
    public Device findDeviceByIdAndUpdate(@PathVariable UUID deviceId, @RequestBody @Validated DeviceDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceService.updateDevice(deviceId,body);
    }


    @DeleteMapping("/{deviceId}") // DELETE 1
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable UUID deviceId){
        deviceService.deleteDevice(deviceId);

    }

    @PatchMapping("/{deviceId}/assign")
    private Device assignDevice(@PathVariable UUID  deviceId, @RequestParam("userId") UUID userId) {
        return deviceService.assign(deviceId, userId);
    }

    @PatchMapping("/{id}/available")
    private Device makeDeviceAvailable(@PathVariable UUID deviceId) {
        return deviceService.makeAvailable(deviceId);
    }

    @PatchMapping("/{id}/dismiss")
    private Device dismissDevice(@PathVariable UUID deviceId) {
        return deviceService.dismiss(deviceId);
    }

    @PatchMapping("/{id}/maintenance")
    private Device startDeviceMaintenance(@PathVariable UUID deviceId) {
        return deviceService.startMaintenance(deviceId);
    }
}
