package demo.transformers;

import demo.dto.BookingDTO;
import demo.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingTransformer implements Transformer<Booking, BookingDTO>{

    @Override
    public BookingDTO fromEntity(Booking booking){
        //trebuie if(booking==null) ??

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setStatus(booking.getStatus());

        return bookingDTO;
    }

    @Override
    public Booking fromDTO(BookingDTO bookingDTO){
        //trebuie if(booking==null) ??

        Booking bookingEntity = new Booking();

        bookingEntity.setBookingId(bookingDTO.getBookingId());
        bookingEntity.setStartDate(bookingDTO.getStartDate());
        bookingEntity.setEndDate(bookingDTO.getEndDate());
        bookingEntity.setStatus(bookingDTO.getStatus());

        //created_at ; uploaded_at?

        return bookingEntity;
    }


}
