package wendydeluca.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wendydeluca.u5d11.entities.Device;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.BadRequestException;
import wendydeluca.u5d11.exceptions.NotFoundException;
import wendydeluca.u5d11.payloads.DeviceDTO;
import wendydeluca.u5d11.repositories.DeviceDAO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    public DeviceDAO deviceDAO;


    @Autowired
    public UserService userService;

    public Page<Device> getAllDevices(int page, int size, String sortBy) { //GET ALL CON PAGINAZIONE
        if (size < 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return deviceDAO.findAll(pageable);
    }

    public Device getDeviceById(UUID deviceId){
        return deviceDAO.findById(deviceId).orElseThrow(()-> new NotFoundException(deviceId));
    }

    public Device saveDevice(DeviceDTO body) {
        Device newDevice = new Device(body.typeOfDevice(), body.state(), userService.getUserById(body.userId()));
        return deviceDAO.save(newDevice);
    }



    public Device updateDevice(UUID deviceId,DeviceDTO body){
        Device found = this.getDeviceById(deviceId);
        found.setTypeOfDevice(body.typeOfDevice());
        found.setState(body.state());
        found.setUser(userService.getUserById(body.userId()));
        deviceDAO.save(found);
        return found;

    }


    public void deleteDevice(UUID deviceId){
        Device found = this.getDeviceById(deviceId);
        deviceDAO.delete(found);
    }


    public Device assign(UUID deviceId, UUID userId) {
        Device device = this.getDeviceById(deviceId);
        User user = userService.getUserById(userId);
        if (Objects.equals(device.getState(), "Dismissed")) throw new BadRequestException("Device ID '" + deviceId + "' is dismissed.");
        else if (Objects.equals(device.getState(), "Maintenance")) throw new BadRequestException("Device ID '" + deviceId + "' is being maintained.");
        else if (device.getUser() == user) throw new BadRequestException("Device ID '" + deviceId +
                "' is already assigned to employee ID '" + userId + "'.");
        else {
            device.setState("Assigned");
            device.setUser(user);
            return deviceDAO.save(device);
        }
    }

    public Device makeAvailable(UUID deviceId) {
        Device found = this.getDeviceById(deviceId);
        if (Objects.equals(found.getState(), "Dismissed")) throw new BadRequestException("Device ID '" + deviceId + "' has been dismissed.");
        else {
            found.setState("Available");
            found.setUser(null);
            return deviceDAO.save(found);
        }
    }

    public Device dismiss(UUID deviceId) {
        Device found = this.getDeviceById(deviceId);
        if (Objects.equals(found.getState(), "Dismissed")) throw new BadRequestException("Device ID '" + deviceId + "' is already dismissed.");
        else {
            found.setState("Dismissed");
            found.setUser(null);
            return deviceDAO.save(found);
        }
    }

    public Device startMaintenance(UUID deviceId) {
        Device found = this.getDeviceById(deviceId);
        if (Objects.equals(found.getState(), "Dismissed")) throw new BadRequestException("Device ID '" + deviceId + "' is dismissed.");
        else if (Objects.equals(found.getState(), "Maintenance")) throw new BadRequestException("Device ID '" + deviceId + "' is already being maintained.");
        else {
            found.setState("Maintenance");
            found.setUser(null);
            return deviceDAO.save(found);
        }
    }
}

