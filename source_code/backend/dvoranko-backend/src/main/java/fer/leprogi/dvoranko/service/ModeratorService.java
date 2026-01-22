package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.*;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.model.*;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.FolderName;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import fer.leprogi.dvoranko.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
public class ModeratorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevRepository;
    @Autowired
    private ZahtjevTerminRepository zahtjevTerminRepository;
    @Autowired
    private TerminRepository terminRepository;
    @Autowired
    private DvoranaRepository dvoranaRepository;
    @Autowired
    private AdresaRepository adresaRepository;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private KategorijaRepository kategorijaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DvoranaService dvoranaService;
    @Autowired
    private TerminService terminService;
    @Autowired
    private MjestoService mjestoService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ZahtjevSlikaRepository zahtjevSlikaRepository;
    @Autowired
    private SlikaDvoranaService slikaDvoranaService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ZahtjevOglasRepository zahtjevOglasRepository;

    @Transactional
    public ZahtjevOglasDTO createAddRequest(CreateZahtjevOglas request, List<MultipartFile> images) throws Exception {
        ZahtjevOglas zahtjev = new ZahtjevOglas();

        User owner = userRepository.findById(request.getIdOwner())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + request.getIdOwner() + " not found"));

        zahtjev.setOwner(owner);
        zahtjev.setNaziv(request.getNaziv());
        zahtjev.setOpis(request.getOpis());
        zahtjev.setKapacitet(request.getKapacitet());
        zahtjev.setPostalCode(request.getPostalCode());
        zahtjev.setCity(request.getCity());
        zahtjev.setStreet(request.getStreet());
        zahtjev.setStreetNumber(request.getStreetNumber());
        zahtjev.setLatitude(request.getLat());
        zahtjev.setLongitude(request.getLng());
        zahtjev.setDaysOpen(request.getDaysOpen());
        zahtjev.setCijenaPoSatu(request.getCijenaPoSatu());

        if (request.getIdKategorije() != null && !request.getIdKategorije().isEmpty()) {
            Set<Kategorija> kategorije = new HashSet<>();
            for (Long idKategorija : request.getIdKategorije()) {
                Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                        .orElseThrow(() -> new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist"));
                kategorije.add(kategorija);
            }
            zahtjev.setKategorije(kategorije);
        }

        ZahtjevOglas saved = zahtjevRepository.saveAndFlush(zahtjev);


            int i = 1;
            for (MultipartFile image : images) {
                String url = cloudinaryService.upload(image, saved.getIdZahtjevOglas(), i, FolderName.zahtjevi);

                ZahtjevSlika slika = new ZahtjevSlika();
                slika.setUrlSlika(url);
                slika.setPoredakSlike("img_" + i);
                slika.setZahtjevOglas(saved);

                saved.getSlike().add(slika);

                zahtjevSlikaRepository.save(slika);
                i++;
            }


        ZahtjevOglas finalSaved = zahtjevRepository.save(saved);

        return dtoMapper.toZahtjevOglasDTO(finalSaved);
    }


    @Transactional
    public Iterable<DvoranaDTO> getAllDvoranaForModerator(CustomOAuth2User principal) {

        Long ownerId = userService.getIdForPrincipal(principal);

        return dvoranaService.getDvoraneByOwner(ownerId);
    }
    @Transactional
    public List<ZahtjevOglasDTO> getAllDvoranaRequestsForModerator(CustomOAuth2User principal) {

        Long ownerId = userService.getIdForPrincipal(principal);
        System.out.println("OWner id je " + ownerId );

        List<ZahtjevOglas> zahtjevi = zahtjevOglasRepository.findAllByOwner_Id(ownerId);
        System.out.println("proslo dalje idemo" + zahtjevi.size());
        return zahtjevi.stream().map(dtoMapper::toZahtjevOglasDTO).toList();
    }

    public Iterable<ZahtjevTerminDTO> getAllTerminRequestsForModerator(CustomOAuth2User principal) {

        Long moderatorId = userService.getIdForPrincipal(principal);

        Set<ZahtjevTermin> sviZahtjevi = new HashSet<>();

        Iterable<Dvorana> dvoraneModerator = dvoranaRepository.findAllByVlasnik_Id(moderatorId);
        for (Dvorana dvorana : dvoraneModerator) {
            Iterable<ZahtjevTermin> zahtjeviDvorana = zahtjevTerminRepository.findByIdDvorana(dvorana.getIdDvorana());
            for (ZahtjevTermin zahtjev : zahtjeviDvorana) {
                sviZahtjevi.add(zahtjev);
            }
        }

        Set<ZahtjevTerminDTO> sviZahtjeviDTO = new HashSet<>();
        for (ZahtjevTermin zahtjev : sviZahtjevi) {
            sviZahtjeviDTO.add(dtoMapper.toZahtjevTerminDTO(zahtjev));
        }

        return sviZahtjeviDTO;
    }

    @Transactional
    public List<TerminZaFrontDTO> getAllTerminRequestsForThisDvorana(CustomOAuth2User principal, long id) {
//        Set<ZahtjevTermin> sviZahtjeviZaOvuDvoranu = new HashSet<>();
//
//        Iterable<ZahtjevTermin> zahtjeviDvorana = zahtjevTerminRepository.findByIdDvorana(id);
//        for(ZahtjevTermin zahtjev: zahtjeviDvorana){
//            sviZahtjeviZaOvuDvoranu.add(zahtjev);
//        }
//
//        Set<ZahtjevTerminDTO> sviZahtjeviDTO = new HashSet<>();
//        for(ZahtjevTermin zahtjev: sviZahtjeviZaOvuDvoranu){
//            sviZahtjeviDTO.add(dtoMapper.toZahtjevTerminDTO(zahtjev));
//        }

        List<ZahtjevTermin> sviTermini = zahtjevTerminRepository.findAllByIdDvorana(id);
        List<TerminZaFrontDTO> result = new ArrayList<>();

//        System.out.println(sviTermini.size());

        for(ZahtjevTermin termin : sviTermini){
            UserDTO user = userService.getUserById(termin.getIdKorisnik());

            TerminZaFrontDTO dto = new TerminZaFrontDTO();
            dto.setId(termin.getId());
            dto.setIdDvorana(id);
            dto.setIdKorisnik(termin.getIdKorisnik());
            dto.setDatumVrijemeEnd(termin.getDatumVrijemeEnd());
            dto.setDatumVrijemeStart(termin.getDatumVrijemeStart());
            dto.setOpisDogadanja(termin.getOpisDogadanja());
            dto.setImeDogadanja(termin.getImeDogadanja());
            dto.setJeJavniEvent(termin.getJeJavniEvent());

            dto.setImeVlasnika(user.getName());

            result.add(dto);
        }

//        return sviZahtjeviDTO;
        return result;
    }

    public Iterable<TerminZaFrontDTO> getAllPotvrdeniTerminiForThisDvorana(CustomOAuth2User principal, long id) throws Exception {
//        Set<Termin> sviPotvrdeniTermini = new HashSet<>();
//        Optional<Dvorana> dvorana = dvoranaRepository.findById(id);
//        if(!dvorana.isPresent()){
//            throw new Exception("error kume ne postoji ta dobro");
//        }
//        List<Termin> sviTermini = new LinkedList<>();
//        sviTermini = terminRepository.findAllByDvorana(dvorana.get());
//
//        Set<TerminDTO> sviTerminiDTO = new HashSet<>();
//        for(Termin termin : sviTermini){
//            sviTerminiDTO.add(dtoMapper.toTerminDTO(termin));
//        }

        Dvorana dvorana = dvoranaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with id " + id + " not found"));

        List<Termin> sviTermini = terminRepository.findAllByDvorana(dvorana);
        List<TerminZaFrontDTO> result = new ArrayList<>();

//        System.out.println(sviTermini.size());

        for(Termin termin : sviTermini){
            UserDTO user = dtoMapper.toUserDTO(termin.getKorisnik());

            TerminZaFrontDTO dto = new TerminZaFrontDTO();
            dto.setId(termin.getId());
            dto.setIdDvorana(id);
            dto.setIdKorisnik(user.getId());
            dto.setDatumVrijemeEnd(termin.getDatumVrijemeEnd());
            dto.setDatumVrijemeStart(termin.getDatumVrijemeStart());
            dto.setOpisDogadanja(termin.getOpisDogadanja());
            dto.setImeDogadanja(termin.getImeDogadanja());
            dto.setJeJavniEvent(termin.getJeJavniEvent());

            dto.setImeVlasnika(user.getName());

            result.add(dto);
        }

        return result;
    }

    @Transactional
    public void approveTerminRequest(Long idZahtjev, CustomOAuth2User principal) throws Exception {

        ZahtjevTermin zahtjev = zahtjevTerminRepository.findById(idZahtjev)
                .orElseThrow(() -> new ResourceNotFoundException("ZahtjevTermin with id " + idZahtjev + " not found for this moderator"));

        User user = userRepository.findById(zahtjev.getIdKorisnik())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + zahtjev.getIdKorisnik() + " not found"));

        Dvorana dvorana = dvoranaRepository.findById(zahtjev.getIdDvorana())
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with id " + zahtjev.getIdDvorana() + " not found"));


        TerminDTO terminDTO = new TerminDTO();
        terminDTO.setDatumVrijemeStart(zahtjev.getDatumVrijemeStart());
        terminDTO.setDatumVrijemeEnd(zahtjev.getDatumVrijemeEnd());
        terminDTO.setJeJavniEvent(zahtjev.getJeJavniEvent());
        terminDTO.setIdKorisnik(zahtjev.getIdKorisnik());
        terminDTO.setIdDvorana(zahtjev.getIdDvorana());
        terminDTO.setOpisDogadanja(zahtjev.getOpisDogadanja());
        terminDTO.setImeDogadanja(zahtjev.getImeDogadanja());

        terminService.create(terminDTO);
        zahtjevTerminRepository.delete(zahtjev);

        mailService.sendMail(user.getEmail(),"Vaš termin je odobren", "Poštovani,\n\nVaš zahtjev za termin u dvorani '" + dvorana.getNazivDvorana() + "', pod nazivom '" + zahtjev.getImeDogadanja() + "' je odobren.\n\nLijep pozdrav,\nDvoranko tim");
    }

    public void rejectTerminRequest(Long idZahtjev, CustomOAuth2User principal) throws Exception {

        ZahtjevTermin zahtjev = zahtjevTerminRepository.findById(idZahtjev)
                .orElseThrow(() -> new ResourceNotFoundException("ZahtjevTermin with id " + idZahtjev + " not found for this moderator"));

        User user = userRepository.findById(zahtjev.getIdKorisnik())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + zahtjev.getIdKorisnik() + " not found"));

        Dvorana dvorana = dvoranaRepository.findById(zahtjev.getIdDvorana())
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with id " + zahtjev.getIdDvorana() + " not found"));

        zahtjevTerminRepository.delete(zahtjev);

        mailService.sendMail(user.getEmail(),"Vaš termin je odbijen", "Poštovani,\n\nVaš zahtjev za termin u dvorani '" + dvorana.getNazivDvorana() + "', pod nazivom '" + zahtjev.getImeDogadanja() + "' je odbijen.\n\nLijep pozdrav,\nDvoranko tim");
    }


    @Transactional
    public DvoranaDTO updateDvorana(Long idDvorana, CreateDvoranaRequest request, List<MultipartFile> images) throws Exception{
        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with idDvorana " + idDvorana + " does not exist"));

//        Adresa adresa = adresaRepository.findById(request.getIdAdresa())
//                .orElseThrow(() -> new ResourceNotFoundException("Adresa with idAdresa " + request.getIdAdresa() + " does not exist"));

        dvorana.setNazivDvorana(request.getNazivDvorana());
        dvorana.setKapacitet(request.getKapacitet());
        dvorana.setCijenaPoSatu(request.getCijenaPoSatu());
        dvorana.setOpis(request.getOpis());
        dvorana.setDaysOpen(request.getDaysOpen());
        //dvorana.setAdresa(adresa);

        if (!request.getIdKategorija().isEmpty()) {
            Set<Kategorija> kategorije = new HashSet<>();
            for (Long idKategorija : request.getIdKategorija()) {
                Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                        .orElseThrow(() -> new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist"));
                kategorije.add(kategorija);
            }
            dvorana.getKategorije().clear();
            dvorana.getKategorije().addAll(kategorije);
        } else {
            dvorana.getKategorije().clear();
        }

        if (images != null) {
            for (int i = 0; i < dvorana.getSlike().size(); i++) {
                MultipartFile newImage = images.get(i);
                SlikaDvorana oldImage = dvorana.getSlike().get(i);

                cloudinaryService.deleteImage(oldImage.getUrlSlika());
                String url = cloudinaryService.upload(newImage, dvorana.getIdDvorana(), i + 1, FolderName.dvorane);

                slikaDvoranaService.updateSlika(oldImage.getIdSlika(), url, oldImage.getPoredakSlike());
            }
        }

        dvorana.setDaysOpen(request.getDaysOpen());

        Dvorana updated = dvoranaRepository.save(dvorana);

        return dtoMapper.toDvoranaDTO(updated);
    }
}
