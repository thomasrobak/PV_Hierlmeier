package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(MedikamentController)
@Mock(Medikament)
class MedikamentControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/medikament/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.medikamentInstanceList.size() == 0
        assert model.medikamentInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.medikamentInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.medikamentInstance != null
        assert view == '/medikament/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/medikament/show/1'
        assert controller.flash.message != null
        assert Medikament.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/medikament/list'


        def medikament = new Medikament()

        // TODO: populate domain properties

        assert medikament.save() != null

        params.id = medikament.id

        def model = controller.show()

        assert model.medikamentInstance == medikament
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/medikament/list'


        def medikament = new Medikament()

        // TODO: populate valid domain properties

        assert medikament.save() != null

        params.id = medikament.id

        def model = controller.edit()

        assert model.medikamentInstance == medikament
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/medikament/list'

        response.reset()


        def medikament = new Medikament()

        // TODO: populate valid domain properties

        assert medikament.save() != null

        // test invalid parameters in update
        params.id = medikament.id

        controller.update()

        assert view == "/medikament/edit"
        assert model.medikamentInstance != null

        medikament.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/medikament/show/$medikament.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/medikament/list'

        response.reset()

        def medikament = new Medikament()

        // TODO: populate valid domain properties
        assert medikament.save() != null
        assert Medikament.count() == 1

        params.id = medikament.id

        controller.delete()

        assert Medikament.count() == 0
        assert Medikament.get(medikament.id) == null
        assert response.redirectedUrl == '/medikament/list'


    }


}